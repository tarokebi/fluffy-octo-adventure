from typing import Callable, Sequence
import flet as ft
from flet_core import Control

from core.routed_view import RoutedView
from views.catalog.index import Catalog
from views.index import Home


class HierarchicalView:
    route: RoutedView
    sub_views: list["HierarchicalView"]

    def __init__(self, route, sub_views=[]):
        self.route = route
        self.sub_views = sub_views

    def gen_view_stack(self, target_route: str, stack: list[ft.View] = None):
        if stack is None:
            stack = []

        path = self.route.get_path()
        if path == target_route or target_route.startswith(
            path + "/"
        ):  # FIXME: use appropriate library
            stack.append(ft.View(self.route, self.route.get_contents()))

        for sub_view in self.sub_views:
            sub_view.gen_view_stack(target_route, stack)

        return stack


# route = {
#     "": (
#         Home,
#         {
#             "/catalog": (Catalog, {}),
#             # "/user": (User, {}),
#         },
#     )
# }


class ViewRouter:
    page: ft.Page
    root: HierarchicalView

    def __init__(self, page: ft.Page):
        self.page = page
        self.root = HierarchicalView(
            route=Home(page, ""),
            sub_views=[
                HierarchicalView(
                    route=Catalog(page, "/catalog"),
                ),
                # HierarchicalView(
                #     route=User(page, "/user"),
                # ),
            ],
        )

    def route_change(self, route):
        self.page.views.clear()

        l_view = self.root.gen_view_stack(route.route)
        for v in l_view:
            self.page.views.append(v)

        self.page.update()
