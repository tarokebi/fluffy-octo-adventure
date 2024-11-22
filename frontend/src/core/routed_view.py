from abc import ABC, abstractmethod
from typing import Optional

import flet as ft
from flet_core import Control


class RoutedView(ABC, ft.View):
    path: str
    parent: Optional["RoutedView"]

    @classmethod
    def get_path(cls) -> str:
        return cls.path

    @classmethod
    def generate(cls, page) -> ft.View:
        contents = cls.get_contents(page)
        view = cls(cls.path, contents)
        return view

    @classmethod
    def gen_view_stack(cls, page, stack=[]) -> list["RoutedView"]:
        stack.append(cls.generate(page))
        if cls.parent is None:
            return stack
        return cls.parent.gen_view_stack(page, stack)

    @classmethod
    @abstractmethod
    def get_contents(cls, page) -> list[Control]:
        raise NotImplementedError()
