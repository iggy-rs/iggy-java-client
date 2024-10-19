rootProject.name = "iggy-java-client"

include("sdk")

include("simple-consumer-example")
project(":simple-consumer-example").projectDir = file("examples/simple-consumer")
