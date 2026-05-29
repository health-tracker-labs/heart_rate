# Собрать и поднять приложение
docker compose up --build
docker compose up --build -d

# Пересобрать без кэша
docker compose build --no-cache

# Пересобрать без кэша и поднять приложение
docker compose build --no-cache && docker compose up -d

# Посмотреть логи
docker compose logs
docker compose logs -f
docker compose logs -f heart-rate

# Остановить compose
docker compose down
docker compose down -v

# Контейнеры
docker ps
docker ps -a

# Образы
docker images

# Зайти в работающий контейнер
docker exec -it heart_rate sh

# Запустить временный контейнер из образа
docker run --rm -it --entrypoint sh health-tracker/heart_rate:1.7.1-SNAPSHOT

# Собрать только build-stage
docker build --target build -t heart-rate-build .

# Зайти в build-stage
docker run --rm -it --entrypoint sh heart-rate-build:latest

# Посмотреть содержимое внутри контейнера
pwd
ls -la
ls -la target
find / -name "*.war"

# История сборки образа
docker history health-tracker/heart_rate:1.7.1-SNAPSHOT