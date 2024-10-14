# foa-frontend

## setup
1. Install [rye](https://rye.astral.sh/guide/installation/)
2. Run `rye sync` in this repository

## run dev-server
1. Run `rye run dev`
2. Access to `http://localhost:3000`

## build as a static website
1. (Prerequisites) Flutter SDK must be installed. See https://docs.flutter.dev/get-started/install.
2. Run `rye run build`
  - Static resources (HTML/CSS/...) is generated at `src/build/web`
3. You can test generated resources by running `rye run host-generated`

## mock
Currently, there is no backend application and instead we use fastAPI to run a mock server.
Run `rye run mock` and then you can use mock server implemented at `mock/mock.py`.
