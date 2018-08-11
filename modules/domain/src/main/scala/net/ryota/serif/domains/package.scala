package net.ryota.serif

import java.time.ZonedDateTime

import net.ryota.validation.Validation
import net.ryota.validation.Validation.{Failures, ValidationResult}

package object domains {
  trait Entity {
    val id: ID[_]
  }

  case class ID[E <: Entity] (value: String) extends AnyVal

  private[domains] def generateID[E <: Entity](): ID[E] = ID(ZonedDateTime.now.toEpochSecond.toString)

  trait Repository[E <: Entity, M[_]] {
    def findById(id: ID[E]): M[Option[Entity]]
    def findByIds(ids: Seq[ID[E]]): M[Seq[E]]
    def store(e: E): M[Unit]
    def delete(id: ID[E]): M[Unit]
  }

  type DomainValidationResult[Entity] = ValidationResult[ValidationFailureMessage ,Entity]

  case class DomainValidation[Entity](getErrors: Entity => Failures[ValidationFailureMessage]) extends Validation[ValidationFailureMessage, Entity](getErrors)
}
