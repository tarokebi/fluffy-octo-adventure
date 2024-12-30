import time

import requests

import flet as ft

from core.routed_view import RoutedView
from config.url import URL_VALIDATE_AUTH
from views.catalog.index import Catalog
from views.login.index import Login
from views.users.index import Users
from views.index import Home

VIEWS: list[RoutedView] = [
    Home,
    Catalog,
    Users,
    Login,
]


class ViewRouter:
    page: ft.Page
    routes: dict[str, RoutedView] = {}

    def __init__(self, page: ft.Page):
        self.page = page
        for view in VIEWS:
            self.routes[view.get_path()] = view

    def route_change(self, route: ft.RouteChangeEvent):
        self.page.views.clear()

        tr = ft.TemplateRoute(route.route)

        # Navigate to login view if not authenticated
        if not self.is_authenticated():
            if not tr.match(Login.get_path()):
                self.page.session.set("redirect_to", route.route)
                self.page.go(Login.get_path())
                return

        head_view = None
        for path, view in self.routes.items():
            if matched := tr.match(path):
                head_view = view
                break

        if not matched:
            self.page.views.append(
                ft.View(
                    "/not_found",
                    [ft.Text(value="NOT FOUND. Redirect to Home in 3 seconds")],
                )
            )
            self.page.update()
            time.sleep(3)
            self.page.go(Home.get_path())
            return

        stack = head_view.gen_view_stack(self.page)

        while len(stack) > 0:
            self.page.views.append(stack.pop())

        self.page.update()

    def is_authenticated(self) -> bool:
        if token := self.page.session.get("token"):
            headers = {"Authorization": f"Bearer {token}"}
            res = requests.get(URL_VALIDATE_AUTH, headers=headers)
            return res.ok

        return False
