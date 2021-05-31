package models

import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.lookup.PlanningId
import org.optaplanner.core.api.domain.variable.PlanningVariable
import io.circe._
import io.circe.generic.semiauto._

@PlanningEntity
case class Lesson (val id: Int, val subject: String, val teacher: String, val studentGroup: String) extends Comparable[Lesson] {

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

    def compareTo(other: Lesson) = internalId compareTo other.internalId
}

object Lesson {

    implicit val encode: Encoder[Lesson] = new Encoder[Lesson] {
        final def apply(l: Lesson): Json = Json.obj(
            ("id", Json.fromInt(l.internalId)),
            ("subject", Json.fromString(l.subject)),
            ("teacher", Json.fromString(l.subject)),
            ("studentGroup", Json.fromString(l.subject)),
            // ("timeslot", Json.fromJsonObject(l.timeslot)),
            // ("room", Json.fromJsonObject(l.room)),
        )
    }

    implicit val decode: Decoder[Lesson] = new Decoder[Lesson] {
        final def apply(l: HCursor): Decoder.Result[Lesson] =
            for {
                id <- l.downField("id").as[Int]
                subject <- l.downField("subject").as[String]
                teacher <- l.downField("teacher").as[String]
                studentGroup <- l.downField("studentGroup").as[String]
            } yield {
                new Lesson(id, subject, teacher, studentGroup)
            }
    }
}