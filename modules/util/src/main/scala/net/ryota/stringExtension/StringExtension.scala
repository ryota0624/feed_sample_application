package net.ryota.stringExtension

object StringExtension {
  implicit class StringExFunctions(value: String) {
    def !(): String = {
      value ++ "!"
    }
  }
}

