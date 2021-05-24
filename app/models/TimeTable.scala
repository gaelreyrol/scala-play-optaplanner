package models

import org.optaplanner.core.api.domain.solution.PlanningSolution
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty
import org.optaplanner.core.api.domain.solution.PlanningScore
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.jdk.CollectionConverters._

@PlanningSolution
case class TimeTable(private val _timeslots: List[Timeslot], private val _rooms: List[Room], private val _lessons: List[Lesson]) {

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "timeslotRange")
    val timeslots: java.util.List[Timeslot] = _timeslots.asJava

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "roomRange")
    val rooms: java.util.List[Room] = _rooms.asJava

    @PlanningEntityCollectionProperty
    val lessons: java.util.List[Lesson] = _lessons.asJava

    @PlanningScore
    var score: HardSoftScore = _;
}

object TimeTable {
    implicit val reads: Reads[TimeTable] = (
        (JsPath \ "timeslots").read[List[Timeslot]] and
        (JsPath \ "rooms").read[List[Room]] and
        (JsPath \ "lessons").read[List[Lesson]]
    ) (TimeTable.apply _)

    implicit  var writes: Writes[TimeTable] = (
        (JsPath \ "timeslots").write[List[Timeslot]] and
        (JsPath \ "rooms").write[List[Room]] and
        (JsPath \ "lessons").write[List[Lesson]]
    ) (unlift(TimeTable.unapply))
}