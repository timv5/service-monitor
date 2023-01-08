# service-monitor
Monitoring (Prometheus) and visualizing metrics (Grafana) services (service-one, service-two) in Springboot.

## Technology stack
- Java 11
- Maven
- Springboot
- Docker, Docker Compose
- Prometheus
- Grafana

## How to run the app?
- In root directory run docker compose file for Grafana and Prometheus: docker-compose -f docker-compose.yml up -d
- run Springboot services:
  - service-two
  - service-one

## How does it work?
when calling service-one from the client, it will call service-two and return it's response.
Prometheus is monitoring on it's dashboard, Grafana is used to show metrics.

## service-one:
- Accessible on: http://localhost:8080
- endpoint: 
  - (GET) /first-service/greeting
  - params: serviceName
  - success response: 200
  - error response: 500
  - calls service-one: http://localhost:8081/second-service/greeting?serviceName=test

## service-two:
- Accessible on: http://localhost:8081
- endpoint:
  - (GET) /second-service/greeting
  - params: serviceName
  - it randomly (50% chance) returns internal server error for the sake of Prometheus monitoring.

## prometheus
- app monitoring
- accessible on: http://localhost:9090/

## grafana
- app metrics
- accessible on: http://localhost:3000/login
- user: admin, pass: admin (you need to change it at first login)
