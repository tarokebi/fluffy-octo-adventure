import flet as ft

import router


def main(page: ft.Page):
    page.title = "Fluffy Octo Adventure"

    r = router.ViewRouter(page)

    def view_pop(view):
        page.views.pop()
        if len(page.views) > 1:
            page.update()
        else:
            page.go("/")

    page.on_route_change = r.route_change
    page.on_view_pop = view_pop
    page.go(page.route)


ft.app(target=main)
