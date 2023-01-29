package mu.two

import mu.two.internal.toStringSafe

public object DefaultMessageFormatter : Formatter {
  public override fun formatMessage(level: mu.two.Level, loggerName: String, msg: () -> Any?): String =
      "${level.name}: [$loggerName] ${msg.toStringSafe()}"

  public override fun formatMessage(
      level: mu.two.Level,
      loggerName: String,
      t: Throwable?,
      msg: () -> Any?
  ): String = "${level.name}: [$loggerName] ${msg.toStringSafe()}${t.throwableToString()}"

  public override fun formatMessage(
      level: mu.two.Level,
      loggerName: String,
      marker: mu.two.Marker?,
      msg: () -> Any?
  ): String = "${level.name}: [$loggerName] ${marker?.getName()} ${msg.toStringSafe()}"

  public override fun formatMessage(
      level: mu.two.Level,
      loggerName: String,
      marker: mu.two.Marker?,
      t: Throwable?,
      msg: () -> Any?
  ): String =
      "${level.name}: [$loggerName] ${marker?.getName()} ${msg.toStringSafe()}${t.throwableToString()}"

  private fun Throwable?.throwableToString(): String {
    if (this == null) {
      return ""
    }
    var msg = ""
    var current = this
    while (current != null && current.cause != current) {
      msg += ", Caused by: '${current.message}'"
      current = current.cause
    }
    return msg
  }
}
