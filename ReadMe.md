# OptaPlanner AI with Play for Scala

> This repository is attempt to port Quarkus [demo](https://quarkus.io/guides/optaplanner) to Scala with Play Framework

## Develop

Run `sbt run` and give it a try:

```bash
curl -i -X POST http://localhost:9000/ \
    -H "Content-Type:application/json" \
    -d '{
        "timeslotList":[
            {
                "dayOfWeek":"MONDAY",
                "startTime":"08:30:00",
                "endTime":"09:30:00"
            },
            {
                "dayOfWeek":"MONDAY",
                "startTime":"09:30:00",
                "endTime":"10:30:00"}
            ],
        "roomList":[
            {
                "name":"Room A"
            },
            {
                "name":"Room B"
            }
        ],
        "lessonList":[
            {
                "id":1,
                "subject":"Math",
                "teacher":"A. Turing",
                "studentGroup":"9th grade"
            },
            {
                "id":2,
                "subject":"Chemistry",
                "teacher":"M. Curie",
                "studentGroup":"9th grade"
            },
            {
                "id":3,
                "subject":"French",
                "teacher":"M. Curie",
                "studentGroup":"10th grade"
            },
            {
                "id":4,
                "subject":"History",
                "teacher":"I. Jones",
                "studentGroup":"10th grade"
            }
        ]
    }'
```