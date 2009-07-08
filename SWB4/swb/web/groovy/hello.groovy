if (!session) {
  session = request.getSession(true)
}

if (!session.counter) {
      session.counter = 1
}

html.html {    // html is implicitly bound to new MarkupBuilder(out)
  head {
      title("Groovy Servlet")
  }
  body {
    p("Hello, ${request.remoteHost}: ${session.counter}! ${new Date()}")
  }
}
session.counter = session.counter + 1