package models

import java.time.{DayOfWeek, LocalTime}
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Timeslot(val dayOfWeek: DayOfWeek, val startTime: LocalTime, val endTime: LocalTime) {
    def this() = this(DayOfWeek.MONDAY, LocalTime.now(), LocalTime.now())

    override def toString(): String = "%s %s - %s".format(this.dayOfWeek, this.startTime, this.endTime)
}

object Timeslot {
    implicit val dayOfWeekFormat = new Format[DayOfWeek] {
        override def reads(json: JsValue): JsResult[DayOfWeek] = json.validate[String].map(DayOfWeek.valueOf)
        override def writes(dayOfWeek: DayOfWeek): JsValue = Json.toJson(dayOfWeek.toString)
    }

    implicit val localTimeFormat = new Format[LocalTime] {
        override def reads(json: JsValue): JsResult[LocalTime] = json.validate[String].map(LocalTime.parse)
        override def writes(localTime: LocalTime): JsValue = Json.toJson(localTime.toString)
    }

    implicit val reads: Reads[Timeslot] = (
        (JsPath \ "dayOfWeek").read[DayOfWeek] and
        (JsPath \ "startTime").read[LocalTime] and
        (JsPath \ "endTime").read[LocalTime]
    ) (Timeslot.apply _)

    implicit val writes: Writes[Timeslot] = (
        (JsPath \ "dayOfWeek").write[DayOfWeek] and
        (JsPath \ "startTime").write[LocalTime] and
        (JsPath \ "endTime").write[LocalTime]
    ) (unlift(Timeslot.unapply))
}
