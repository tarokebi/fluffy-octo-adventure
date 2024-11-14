import flet as ft
from flet_core.control import Control

from core.routed_view import RoutedView
from views.index import Home


class Catalog(RoutedView):
    path = "/catalog"
    parent = Home

    @classmethod
    def get_contents(cls, page) -> list[Control]:
        contents = [
            ft.AppBar(title=ft.Text("Flet app"), bgcolor=ft.colors.SURFACE_VARIANT),
            ft.Text("カタログ画面"),
        ]

        return contents
