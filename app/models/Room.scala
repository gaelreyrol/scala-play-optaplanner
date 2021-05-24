package models

import play.api.libs.json._

case class Room(val name: String) {
    override def toString(): String = this.name
}

object Room {
    implicit val reads: Reads[Room] = Json.reads[Room]
    implicit val writes: Writes[Room] = Json.writes[Room]
}