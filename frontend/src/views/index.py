import flet as ft
from flet_core import Control

from core.routed_view import RoutedView


class Home(RoutedView):
    path = "/"
    parent = None

    @classmethod
    def get_contents(self, page) -> list[Control]:
        contents = [
            ft.AppBar(
                title=ft.Text("Fluffy Octo Adventure"),
                bgcolor=ft.colors.SURFACE_VARIANT,
                actions=[
                    ft.PopupMenuButton(
                        items=[
                            ft.PopupMenuItem(
                                text="Users",
                                checked=False,
                                on_click=lambda e: e.page.go("/users"),
                            ),
                            ft.PopupMenuItem(
                                text="Catalog",
                                checked=False,
                                on_click=lambda e: e.page.go("/catalog"),
                            ),
                            ft.PopupMenuItem(),  # divider
                            ft.PopupMenuItem(
                                text="Login",
                                icon=ft.icons.LOGIN,
                                checked=False,
                                on_click=lambda e: e.page.go("/login"),
                            ),
                        ]
                    ),
                ],
            ),
            ft.Text("Welcome to FOA!!")
        ]

        return contents
