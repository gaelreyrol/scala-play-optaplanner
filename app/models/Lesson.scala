package models

import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.lookup.PlanningId
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import play.api.libs.json._

@PlanningEntity
case class Lesson (val id: Int, val subject: String, val teacher: String, val studentGroup: String) {
    @PlanningId
    val internalId: Int = id

    @PlanningVariable(valueRangeProviderRefs = Array("timeslotRange"))
    var _timeslot: Timeslot = _

    @PlanningVariable(valueRangeProviderRefs = Array("roomRange"))
    var _room: Room = _

    def timeslot = _timeslot
    def timeslot_= (timeslot: Timeslot) = this._timeslot = timeslot
    
    def room = _room
    def room_= (room: Room) = this._room = room

    def this() = this(0, "", "", "")

    override def toString(): String = "%s (%d) - %s : %s".format(this.subject, this.id, this.teacher, this.studentGroup)
}

object Lesson {
    implicit val reads: Reads[Lesson] = Json.reads[Lesson]
    implicit val writes: Writes[Lesson] = Json.writes[Lesson]
}