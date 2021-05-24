package models

import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.lookup.PlanningId
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import play.api.libs.json._

@PlanningEntity
case class Lesson (@PlanningId val id: Int, val subject: String, val teacher: String, val studentGroup: String) {
    @PlanningVariable(valueRangeProviderRefs = Array("timeslotRange"))
    var _timeslot: Timeslot = _

    @PlanningVariable(valueRangeProviderRefs = Array("roomRange"))
    var _room: Room = _

    def timeslot = _timeslot
    def timeslot_= (timeslot: Timeslot) = this._timeslot = timeslot
    
    def room = _room
    def room_= (room: Room) = this._room = room

    override def toString(): String = "%s (%d)".format(this.subject, this.id)
}

object Lesson {
    implicit val reads: Reads[Lesson] = Json.reads[Lesson]
    implicit val writes: Writes[Lesson] = Json.writes[Lesson]
}