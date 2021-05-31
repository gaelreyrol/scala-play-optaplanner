package models

import org.optaplanner.core.api.domain.solution.PlanningSolution
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty
import org.optaplanner.core.api.domain.solution.PlanningScore
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import scala.jdk.CollectionConverters._
import io.circe._
import io.circe.generic.semiauto._

@PlanningSolution
case class TimeTable(private val _timeslots: List[Timeslot], private val _rooms: List[Room], private val _lessons: List[Lesson]) {

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "timeslotRange")
    var timeslots: java.util.List[Timeslot] = _timeslots.asJava

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "roomRange")
    var rooms: java.util.List[Room] = _rooms.asJava

    @PlanningEntityCollectionProperty
    var lessons: java.util.List[Lesson] = _lessons.asJava

    @PlanningScore
    var score: HardSoftScore = _;

    def this() = this(List(), List(), List())
}

object TimeTable {
    implicit val encode: Encoder[TimeTable] = new Encoder[TimeTable] {
        final def apply(t: TimeTable): Json = Json.obj(
            // ("timeslots", Json.fromValues(t.timeslots)),
            // ("rooms", Json.fromValues(t.rooms)),
            // ("lessons", Json.fromValues(t.lessons)),
            ("score", Json.fromString(t.score.toString()))
        )
    }

    implicit val decode: Decoder[TimeTable] = new Decoder[TimeTable] {
        final def apply(t: HCursor): Decoder.Result[TimeTable] =
            for {
                timeslots <- t.downField("timeslots").as[List[Timeslot]]
                rooms <- t.downField("rooms").as[List[Room]]
                lessons <- t.downField("lessons").as[List[Lesson]]
            } yield {
                new TimeTable(timeslots, rooms, lessons)
            }
    }
}