import flet as ft
from flet_core.control import Control
from core.routed_view import RoutedView


class Home(RoutedView):
    def get_contents(self) -> list[Control]:
        contents = [
            ft.AppBar(title=ft.Text("Flet app"), bgcolor=ft.colors.SURFACE_VARIANT),
            ft.ElevatedButton("Go to catalog", on_click=self.open_mail_settings),
        ]
        return contents

    def open_mail_settings(self, e):
        self.page.go("/catalog")
