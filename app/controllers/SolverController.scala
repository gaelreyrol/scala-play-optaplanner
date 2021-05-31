package controllers

import javax.inject._
import java.util.UUID
import models._
import play.api._
import play.api.mvc._
import play.api.libs.circe._
import org.optaplanner.core.api.solver.SolverJob
import org.optaplanner.core.api.solver.SolverManager
import org.optaplanner.core.config.solver.SolverConfig
import org.optaplanner.core.config.solver.SolverManagerConfig
import io.circe.generic.auto._
import io.circe.syntax._

@Singleton
class SolverController @Inject() (val controllerComponents: ControllerComponents) extends BaseController with Circe {

  def index() = Action(circe.json[TimeTable]) { implicit request =>
    val solverManager: SolverManager[TimeTable, UUID] = SolverManager.create(
      SolverConfig.createFromXmlResource("solverconfig.xml"),
      new SolverManagerConfig()
    )
    val solverJob: SolverJob[TimeTable, UUID] = solverManager.solve(UUID.randomUUID(), request.body)
    val solution: TimeTable = solverJob.getFinalBestSolution()

    Ok(solution.asJson)
  }
}
