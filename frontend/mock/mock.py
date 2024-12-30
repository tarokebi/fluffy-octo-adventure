from datetime import datetime, timedelta, timezone

from fastapi import Depends, FastAPI, HTTPException, status
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import JSONResponse
from fastapi.security import OAuth2PasswordBearer
from jose import JWTError, jwt
from pydantic import BaseModel

ACCESS_TOKEN_EXPIRE_MINUTES = 10.0
SECRET_KEY = "secret"
ALGORITHM = "HS256"
LOGIN_USERS = {"user": {"password": "pass"}}
USERS = [
    {
        "id": 1,
        "name": "Rob Halford",
        "email": "rob.halford@rocknroll.co",
        "createdAt": "2024-01-01T06:00:00",
        "updatedAt": "2024-01-01T07:00:00",
    },
    {
        "id": 2,
        "name": "Brian Johnson",
        "email": "brian.johnson@rocknroll.co",
        "createdAt": "2024-01-02T06:00:00",
        "updatedAt": "2024-01-02T07:00:00",
    },
    {
        "id": 3,
        "name": "Angus Young",
        "email": "angus.young@rocknroll.co",
        "createdAt": "2024-01-03T06:00:00",
        "updatedAt": "2024-01-03T07:00:00",
    },
    {
        "id": 4,
        "name": "Axl Rose",
        "email": "axl.rose@rocknroll.co",
        "createdAt": "2024-01-04T06:00:00",
        "updatedAt": "2024-01-04T07:00:00",
    },
    {
        "id": 5,
        "name": "Paul Gilbert",
        "email": "paul.gilbert@rocknroll.co",
        "createdAt": "2024-01-05T06:00:00",
        "updatedAt": "2024-01-05T07:00:00",
    },
    {
        "id": 6,
        "name": "Ozzy Osbourne",
        "email": "ozzy.osbourne@rocknroll.co",
        "createdAt": "2024-01-06T06:00:00",
        "updatedAt": "2024-01-06T07:00:00",
    },
    {
        "id": 7,
        "name": "Michael Schenker",
        "email": "michael.schenker@rocknroll.co",
        "createdAt": "2024-01-07T06:00:00",
        "updatedAt": "2024-01-07T07:00:00",
    },
]


app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=[
        "http://localhost:3000",
        "http://127.0.0.1:3000",
    ],
    allow_methods=["*"],
    allow_headers=["*"],
)


oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")


class Token(BaseModel):
    access_token: str
    token_type: str


class AuthRequest(BaseModel):
    username: str
    password: str


@app.get("/")
async def root():
    return JSONResponse(content={"status": "ok"})


@app.post("/api/auth")
async def auth(req: AuthRequest) -> Token:
    user = LOGIN_USERS.get(req.username)
    if user:
        if user.get("password") == req.password:
            access_token_expires = timedelta(minutes=ACCESS_TOKEN_EXPIRE_MINUTES)
            access_token = create_access_token(
                data={"sub": req.username}, expires_delta=access_token_expires
            )
            return Token(access_token=access_token, token_type="bearer")

    raise HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="Incorrect username or password",
        headers={"WWW-Authenticate": "Bearer"},
    )


def create_access_token(data: dict, expires_delta: timedelta | None = None):
    to_encode = data.copy()
    if expires_delta:
        expire = datetime.now(timezone.utc) + expires_delta
    else:
        expire = datetime.now(timezone.utc) + timedelta(minutes=15)
    to_encode.update({"exp": expire})
    encoded_jwt = jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)
    return encoded_jwt


async def get_current_user(token: str = Depends(oauth2_scheme)):
    credentials_exception = HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="Could not validate credentials",
        headers={"WWW-Authenticate": "Bearer"},
    )
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
        username: str = payload.get("sub")
        if username is None:
            raise credentials_exception
    except JWTError:
        raise credentials_exception
    return username


@app.get("/api/validate-auth")
async def validate_auth(user: str = Depends(get_current_user)):
    return {"user": user}


@app.get("/api/users")
async def read_users(_: str = Depends(get_current_user)):
    return USERS


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8080)
