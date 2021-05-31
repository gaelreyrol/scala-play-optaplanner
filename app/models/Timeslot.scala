package models

import java.time.{DayOfWeek, LocalTime}
import io.circe._
import io.circe.generic.semiauto._
import scala.util.Try

case class Timeslot(val dayOfWeek: DayOfWeek, val startTime: LocalTime, val endTime: LocalTime) {
    def this() = this(DayOfWeek.MONDAY, LocalTime.now(), LocalTime.now())

    override def toString(): String = "%s %s - %s".format(this.dayOfWeek, this.startTime, this.endTime)
}

object Timeslot {
    implicit val encodeDayOfWeek: Encoder[DayOfWeek] = Encoder.encodeString.contramap[DayOfWeek](_.toString)

    implicit val decodeDayOfWeek: Decoder[DayOfWeek] = Decoder.decodeString.emapTry { str =>
        Try(DayOfWeek.valueOf(str))
    }

    implicit val encode: Encoder[Timeslot] = new Encoder[Timeslot] {
        final def apply(t: Timeslot): Json = Json.obj(
            ("dayOfWeek", Json.fromString(t.dayOfWeek.toString())),
            ("startTime", Json.fromString(t.startTime.toString())),
            ("endTime", Json.fromString(t.endTime.toString())),
        )
    }

    implicit val decode: Decoder[Timeslot] = new Decoder[Timeslot] {
        final def apply(t: HCursor): Decoder.Result[Timeslot] =
            for {
                dayOfWeek <- t.downField("dayOfWeek").as[DayOfWeek]
                startTime <- t.downField("startTime").as[LocalTime]
                endTime <- t.downField("endTime").as[LocalTime]
            } yield {
                new Timeslot(dayOfWeek, startTime, endTime)
            }
    }
}
