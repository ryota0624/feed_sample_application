package net.ryota.serif.domains

import net.ryota.stringExtension.StringExtension._

case class Serif(text: String) {
  def emphasis(): String = text.!()
}
