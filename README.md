# pa_alpan


#BloodSugarSimulator


Test & Build:
```
        mvn clean install
```

Test:
```
        mvn test
```

Run:
```
        mvn spring-boot:run
```



Some ways to push metrics:

```
curl -v http://localhost:8080/api/food -H "Content-Type: application/json" -X POST -d '{ "metricName":"Kaiser roll"}'
```

```
curl -v http://localhost:8080/api/exercise -H "Content-Type: application/json" -X POST -d '{ "metricName":"Sprinting"}'
```
