import http
import flet as ft
import requests
from core.routed_view import RoutedView
from flet_core.control import Control
from config.url import URL_USERS
from views.login.index import Login
from views.index import Home


class Users(RoutedView):
    path = "/users"
    parent = Home

    @classmethod
    def get_contents(cls, page) -> list[Control]:
        contents = [
            ft.AppBar(title=ft.Text("ユーザ管理"), bgcolor=ft.colors.SURFACE_VARIANT),
            ft.DataTable(
                columns=[
                    ft.DataColumn(ft.Text("ID"), numeric=True),
                    ft.DataColumn(ft.Text("Name")),
                    ft.DataColumn(ft.Text("Email")),
                ],
                rows=[
                    ft.DataRow(
                        cells=[
                            ft.DataCell(ft.Text(user["id"])),
                            ft.DataCell(ft.Text(user["name"])),
                            ft.DataCell(ft.Text(user["email"])),
                        ],
                    )
                    for user in cls.fetch_users(page)
                ],
            ),
        ]

        return contents

    @classmethod
    def fetch_users(cls, page: ft.Page):
        if token := page.session.get("token"):
            headers = {"Authorization": f"Bearer {token}"}
            res = requests.get(URL_USERS, headers=headers)
            if res.ok:
                return res.json()
            elif res.status_code != http.HTTPStatus.UNAUTHORIZED:
                alert = ft.AlertDialog(
                    title=ft.Text(f"API failed. ({res.status_code})")
                )
                return []


        def go_to_login(e):
            page: ft.Page = e.page
            page.session.set("redirect_to", cls.get_path())
            page.go(Login.get_path())
            page.close(alert)
            return

        alert = ft.AlertDialog(
            title=ft.Text("Please Login to Continue."),
            actions=[
            ft.TextButton("Go to Login Page", on_click=go_to_login),
            ]
        )
        page.open(alert)

        return []
