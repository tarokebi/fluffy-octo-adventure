import json

import flet as ft
import requests
from flet_core.control import Control

from config.url import URL_AUTH
from core.routed_view import RoutedView
from views.index import Home


class Login(RoutedView):
    path = "/login"
    parent = None

    @classmethod
    def get_contents(cls, page: ft.Page) -> list[Control]:
        tfUsername = ft.TextField(label="Username", autofocus=True)
        tfPassword = ft.TextField(
            label="Password", password=True, can_reveal_password=True
        )

        def login(_):
            url = URL_AUTH
            headers = {"Content-Type": "application/json"}
            payload = {
                "username": tfUsername.value,
                "password": tfPassword.value,
            }

            res = requests.post(url, headers=headers, data=json.dumps(payload))

            if res.status_code == 200:
                response_data = res.json()
                if token := response_data.get("access_token"):
                    page.session.set("token", token)
                    if redirect_to := page.session.get("redirect_to"):
                        page.session.remove("redirect_to")
                        page.go(redirect_to)
                    else:
                        page.go(Home.get_path())
                page.update()
            else:
                alert = ft.AlertDialog(
                    title=ft.Text("Authentication failed."),
                )
                page.open(alert)

        btnLogin = ft.ElevatedButton("Login", on_click=login)

        contents = [
            ft.AppBar(
                title=ft.Text("Fluffy Octo Adventure"),
                bgcolor=ft.colors.SURFACE_VARIANT,
            ),
            tfUsername,
            tfPassword,
            btnLogin,
        ]

        return contents
