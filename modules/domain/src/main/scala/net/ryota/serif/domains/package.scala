package net.ryota.serif

package object domains {
  trait Entity {
    val id: ID[_]
  }

  class ID[E <: Entity](val value: String) extends AnyVal

  trait Repository[E <: Entity, M[_]] {
    def findById(id: ID[E]): M[Entity]
    def findByIds(ids: Seq[ID[E]]): M[Seq[E]]
    def store(e: E): M[Unit]
    def delete(id: ID[E]): M[Unit]
  }
}
