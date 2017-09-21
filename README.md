# kml2json
kml2json is a simple tool that takes polygons in KML files and turns them into JSON

## Limitations
The only limitation I've come across is that input KML files can only contain one polygon. I'm not sure why and I will work to find the cause of this bug.

## Usage
Usage is the same across all operating systems:  
```kml2json.jar <kml file path>```  
You can use either relative file paths or relative ones.

## Never used Kotlin?
Neither had I! This project was my first time using Kotlin extensively. Please see the [Kotlin website](https://kotlinlang.org/) for help developing with Kotlin.

I've also included an `outputSpec.json` file with an example of the proper format of the outputted JSON. A sample KML with a rough polygon of the ZBW airspace is also included.