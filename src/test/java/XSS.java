import com.gmail.kiiiiiim1005.diary.util.Strings;

public class XSS {

    public static void main(String[] args) {
        String a = "<script> alert('hi');var a= 'hello'</script  >";
        System.out.println(Strings.removeScriptTags(a));
    }

}
