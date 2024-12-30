import flet as ft
import requests
from config import FOA_BACKEND_ENDPOINT
from core.routed_view import RoutedView
from flet_core.control import Control
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
                    for user in cls.fetch_users()
                ],
            ),
        ]

        return contents

    @classmethod
    def fetch_users(cls):
        res = requests.get(f"{FOA_BACKEND_ENDPOINT}/api/users")
        return res.json()
