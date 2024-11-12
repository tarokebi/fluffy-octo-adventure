from abc import ABC, abstractmethod
from typing import List, Sequence
import flet as ft
from flet_core import Control


class RoutedView(ABC):
    page: ft.Page
    path: str

    def __init__(self, page: ft.Page, path: str):
        self.page = page
        self.path = path

    def get_path(self) -> str:
        return self.path

    @abstractmethod
    def get_contents(self) -> Sequence[Control]:
        pass
