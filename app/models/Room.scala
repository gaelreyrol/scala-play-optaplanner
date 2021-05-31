package models

import io.circe._
import io.circe.generic.semiauto._

case class Room(val name: String) {
    override def toString(): String = this.name

    def this() = this("")
}

object Room {
    implicit val encode: Encoder[Room] = new Encoder[Room] {
        final def apply(r: Room): Json = Json.obj(
            ("name", Json.fromString(r.name))
        )
    }

    implicit val decode: Decoder[Room] = new Decoder[Room] {
        final def apply(r: HCursor): Decoder.Result[Room] =
            for {
                name <- r.downField("name").as[String]
            } yield {
                new Room(name)
            }
    }
}