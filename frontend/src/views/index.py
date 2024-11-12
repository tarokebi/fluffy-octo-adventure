import flet as ft
from flet_core.control import Control

from core.routed_view import RoutedView


class Home(RoutedView):
    path = "/"
    parent = None

    @classmethod
    def get_contents(self, page) -> list[Control]:
        def open_mail_settings(e):
            page.go("/catalog")

        contents = [
            ft.AppBar(title=ft.Text("Flet app"), bgcolor=ft.colors.SURFACE_VARIANT),
            ft.ElevatedButton("Go to catalog", on_click=open_mail_settings),
        ]

        return contents
