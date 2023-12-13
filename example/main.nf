include { detectObjects } from 'plugin/nf-djl'

def urls = [
        "https://informo.madrid.es/cameras/Camara06303.jpg?v=1234",
        "https://informo.madrid.es/cameras/Camara06301.jpg?v=1234",
        "http://djl.ai/examples/src/test/resources/dog_bike_car.jpg",
        "http://djl.ai/examples/src/test/resources/kana1.jpg",
        "http://djl.ai/examples/src/test/resources/kana2.jpg",
]


class UrlObjects{
    String url
    List objects
    List toList(){
        objects.inject([],{list, obj->list << [url,obj]})
    }
}

process DETECT_OBJECTS{
    input:
        val url
    exec:
        ret = new UrlObjects(url:url, objects: detectObjects(url))
    output:
        val( ret )
}

workflow {
    channel.from(urls)
            | DETECT_OBJECTS
            | flatMap { obj -> obj.toList() }
            | view
}