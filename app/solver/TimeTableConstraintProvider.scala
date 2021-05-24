package solver

import models.Lesson
import org.optaplanner.core.api.score.stream.{Constraint, ConstraintFactory, ConstraintProvider, Joiners}
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore

class TimeTableConstraintProvider extends ConstraintProvider {
    def defineConstraints(constraintFactory: ConstraintFactory): Array[Constraint] = Array(
        roomConflict(constraintFactory),
        teacherConflict(constraintFactory),
    )

    private def roomConflict(constraintFactory: ConstraintFactory): Constraint = 
        constraintFactory
            .from(classOf[Lesson])
            .join(
                classOf[Lesson],
                Joiners.equal((lesson: Lesson) => lesson.timeslot),
                Joiners.equal((lesson: Lesson) => lesson.room)
            )
            .penalize("Room conflict", HardSoftScore.ONE_HARD)

    private def teacherConflict(constraintFactory: ConstraintFactory): Constraint = 
        constraintFactory
            .fromUniquePair(
                classOf[Lesson],
                Joiners.equal((lesson: Lesson) => lesson.timeslot),
                Joiners.equal((lesson: Lesson) => lesson.teacher)
            )
            .penalize("Teacher conflict", HardSoftScore.ONE_HARD)

    private def studentGroupConflict(constraintFactory: ConstraintFactory): Constraint = 
        constraintFactory
            .fromUniquePair(
                classOf[Lesson],
                Joiners.equal((lesson: Lesson) => lesson.timeslot),
                Joiners.equal((lesson: Lesson) => lesson.studentGroup)
            )
            .penalize("Student group conflict", HardSoftScore.ONE_HARD)
}