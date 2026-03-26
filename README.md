# logging-system

Spring Boot + MongoDB logging service with two APIs:

- `POST /logs` to add a log entry
- `GET /logs` to query log entries

## Requirements

- Java 17+
- MongoDB running locally (or provide a Mongo URI)

## Configure

Set MongoDB URI via environment variable (recommended):

```bash
export MONGODB_URI="mongodb://localhost:27017/logging_system"
```

Or edit `src/main/resources/application.yml`.

## Start MongoDB locally (manual package install)

```bash
cd "/Users/sashwat_psingh/Desktop/VS code/mongodb-macos-aarch64--8.2.6"
mkdir -p data/db
./bin/mongod --dbpath "./data/db" --bind_ip 127.0.0.1 --port 27017
```

Note: this path is specific to where your MongoDB Community Server package is downloaded/extracted on your machine. Update the `cd` path accordingly.

## Run

```bash
./mvnw spring-boot:run
```

If you don’t have Maven wrapper, run with Maven:

```bash
mvn spring-boot:run
```

## API

### Add a log

```bash
curl -X POST "http://localhost:8080/logs" \
  -H "Content-Type: application/json" \
  -d '{"level":"info","message":"service started"}'
```

### Query logs

```bash
curl "http://localhost:8080/logs?level=info&from=2026-03-26T00:00:00Z&size=20"
```

Supported query params:

- `level`: debug|info|warn|error
- `from`, `to`: ISO-8601 instants (e.g. `2026-03-26T00:00:00Z`)
- `q`: substring match on `message` (case-insensitive)
- `page` (default 0), `size` (default 50, max 500)

