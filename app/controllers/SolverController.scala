package controllers

import javax.inject._
import java.util.UUID
import play.api._
import play.api.mvc._
import play.api.libs.json._
import org.optaplanner.core.api.solver.SolverJob
import org.optaplanner.core.api.solver.SolverManager
import org.optaplanner.core.config.solver.SolverConfig
import org.optaplanner.core.config.solver.SolverManagerConfig
import models._
import scala.io.Source

@Singleton
class SolverController @Inject() (val controllerComponents: ControllerComponents) extends BaseController {

  def index() = Action(parse.json) { request =>
    val timetable = request.body.validate[TimeTable]

    timetable.fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      timetable => {
        val solverManager: SolverManager[TimeTable, UUID] = SolverManager.create(
          SolverConfig.createFromXmlResource("solverconfig.xml"),
          new SolverManagerConfig()
        )
        val solverJob: SolverJob[TimeTable, UUID] = solverManager.solve(UUID.randomUUID(), timetable)
        val solution: TimeTable = solverJob.getFinalBestSolution()

        Ok(Json.toJson(solution))
      }
    )
  }
}
