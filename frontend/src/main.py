import flet as ft
import requests


def main(page: ft.Page):
    page.title = "FastAPI Mock Server Client"

    status_text = ft.Text(size=20)

    def get_status(e):
        status_text.value = "Fetching status..."
        page.update()

        try:
            response = requests.get("http://localhost:8080/api")
            data = response.json()
            status_text.value = f"Status: {data['status']}"
        except requests.RequestException as exc:
            status_text.value = f"Error: {str(exc)}"

        page.update()

    page.add(
        ft.Column(
            [
                ft.ElevatedButton("Get Status", on_click=get_status),
                status_text,
            ],
            horizontal_alignment=ft.CrossAxisAlignment.CENTER,
        )
    )


ft.app(target=main)
