<script type="text/javascript">
    const colorList = [
        '#F9F900',
        '#6FB7B7',
        '#9999CC',
        '#B766AD',
        '#B87070',
        '#FF8F59',
        '#FFAF60',
        '#FFDC35',
        '#FFFF37',
        '#B7FF4A',
        '#28FF28',
        '#1AFD9C',
        '#00FFFF',
        '#2894FF',
        '#6A6AFF',
        '#BE77FF',
        '#FF77FF',
        '#FF79BC',
        '#FF2D2D',
        '#ADADAD'
    ]
    const colorListLength = 20
    const gogsHostPath = "corporate" ;
    const gogsPort = "";
    const HostPath = "localhost:8080"
    const mappedPackage = '/FileLibrarys/FileLibrary/'
    function getRandColor () {
        var tem = Math.round(Math.random() * colorListLength)
        return colorList[tem]
    }
    export default
    {
        colorList,
        colorListLength,
        getRandColor,
        gogsHostPath,
        HostPath,
        gogsPort,
        mappedPackage
    }
</script>