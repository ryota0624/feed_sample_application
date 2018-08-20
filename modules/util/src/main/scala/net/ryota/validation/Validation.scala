package net.ryota.validation

import net.ryota.validation.Validation._

class Validation[Failure, Entity](private val getErrors: Entity => Failures[Failure]) {
  def run(e: Entity): ValidationResult[Failure, Entity] = {
    val errors = getErrors(e)
    if (errors.isEmpty) Right(e)
    else Left(errors)
  }

  def and[F <: Failure](other: Validation[Failure, Entity]): Validation[Failure, Entity] = new Validation ({ (entity: Entity) =>
    other.getErrors(entity) ++ getErrors(entity)
  })

}


object Validation {
  type Failures[F] = Seq[F]
  type ValidationResult[F, S] = Either[Failures[F], S]

  def ifTextLengthOver[F](length: Int)(failures: Failures[F]) = new Validation[F, String]({ (text: String) =>
    if (text.length > length) failures
    else Nil
  })

  def ifTextLengthLess[F](length: Int)(failures: Failures[F]) = new Validation[F, String]({ (text: String) =>
    if (text.length < length) failures
    else Nil
  })

  def ifTextMatchedPattern[F](pattern: String)(failures: Failures[F]) = new Validation[F, String]({ (text: String) =>
    if (text.matches(pattern)) failures
    else Nil
  })
}