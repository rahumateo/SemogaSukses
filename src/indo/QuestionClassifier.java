package indo;

import indo.objects.PreAnalyzedQuestion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Rahmat Hidayah SB
 */
public class QuestionClassifier {

    private static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(System.out));

    private static final String EOS = "(.*)(?-i)$";
    private static final String Q_WORDS_1 = "(?i)^(Dengan )?(siapa)(kah)? (.)*(\\?)?(?-i)$";
    private static final String Q_WORDS_2 = "(?i)^(.)*?(berapa)(kah)? (.)*(\\?)?(?-i)$";
    private static final String Q_WORDS_3 = "(?i)^(Mengapa|Kenapa)(kah)? (.)*(\\?)?(?-i)$";
    private static final String Q_WORDS_4 = "(?i)^(Kapan)(kah)? (.)*(\\?)?(?-i)$";
    private static final String Q_WORDS_5 = "(?i)^(.)*(apa)(kah)? (.)*(\\?)?(?-i)$";
    private static final String Q_WORDS_6 = "(?i)^(Di)(.*)(mana)(kah)? (.)*(\\?)?(?-i)$";
    private static final String Q_WORDS_7 = "(?i)^(Bagaimana)(kah)? (.)*(\\?)?(?-i)$";

    private static String processed_q;
    private static final ArrayList<String> question_list = new ArrayList<>();

    private static String qw;
    private static String qt;
    private static HashSet<String> at;

    public static String getQw() {
        return qw;
    }

    public static String getQt() {
        return qt;
    }

    public static String[] getAt() {
        String[] anT = at.toArray(new String[at.size()]);
        return anT;
    }
    
    /**
     * Determine which question word is used in the question.
     *
     * @param question
     * @return an PreAnalyzedQuestion
     */
    public static String detectQWord(String question) {
        at = new HashSet<>();

        processed_q = remove_Q_mark(question);

        if (processed_q.matches(Q_WORDS_1)) {
            processed_q = q_siapa(processed_q);
        } else if (processed_q.matches(Q_WORDS_2)) {
            processed_q = q_berapa(processed_q);
        } else if (processed_q.matches(Q_WORDS_3)) {
            processed_q = q_mengapa(processed_q);
        } else if (processed_q.matches(Q_WORDS_4)) {
            processed_q = q_kapan(processed_q);
        } else if (processed_q.matches(Q_WORDS_5)) {
            processed_q = q_apa(processed_q);
        } else if (processed_q.matches(Q_WORDS_6)) {
            processed_q = q_dimana(processed_q);
        } else if (processed_q.matches(Q_WORDS_7)) {
            processed_q = q_bagaimana(processed_q);
        } else {
            System.out.println("No matched question type. Please insert a valid question.");
            return "";
        }
        
        return processed_q;
    }

    /**
     * Remove question mark at the end of the question, if any.
     *
     * @param <code>String<code/> question
     */
    private static String remove_Q_mark(String question) {
        while (question.matches(".*\\Q?\\E")) {
            question = question.replaceAll("\\Q?\\E$", "");
        }
        return question;
    }

    /**
     * Handles if the question type is <i>Apa(kah)<i/>.
     *
     * @param string <code>String<code/> question
     * @return nothing
     *
     * - Apa(kah) + nomina (istilah|benda) - Apa(kah) + yang dimaksud (dengan) +
     * nomina (istilah/benda) - Apa(kah) + definisi (dari) +
     * nomina(istilah|benda) - Apa(kah) + nomina(benda atribut) + (dari) +
     * nomina(geografi|orang)
     */
    private static String q_apa(String question) {
       // System.out.print("Type : Apa --> ");
        String ATTR = "(nama )";

        String definisi = "(?i)^(apa)(kah)? (yang (di( )?maksud |biasa di( )?sebut )(dengan )?"
                + "|(definisi |arti )(dari )?)";
        String ibukota = "(?i)^(apa)(kah)? (ibu( )?kota) (dari )?";
        String sinonim = "(?i)^(apa)(kah)? (nama lain |sinonim )(dari )?";
        String abbr = "(?i)^(apa)(kah)? (singkatan |kependekan |kepanjangan )(dari )?";
        String atribut = "(?i)^(apa)(kah)? " + ATTR + "([\\w ]+)(dari )?";
        String benda1 = "(?i)^(apa)(kah)? ([\\w ]+)(yang )";
        String benda2 = "(?i)^([\\w ]+)(apa)(kah)? ";
        
        if (Pattern.matches(definisi + EOS, question)) { System.out.println("Definisi");
            question = question.replaceFirst(definisi, "");
            qw = "what is ";
            qt = "DEFINITION";
            at.add("Definition");
        } else if (Pattern.matches(sinonim + EOS, question)) { System.out.println("Sinonim");
            question = question.replaceFirst(sinonim, "");
            qw = "what is the synonym of ";
            qt = "SYNONYM";
            at.add("NEproperName");
        } else if (Pattern.matches(ibukota + EOS, question)) { System.out.println("Ibukota");
            question = question.replaceFirst(ibukota, "capital of ");
            qw = "what is the ";
            qt = "CAPITAL";
            at.add("NElocation->NEcity->NEcapital");
        } else if (Pattern.matches(abbr + EOS, question)) { System.out.println("Singkatan");
            qw = "what is the ";
            String temp = question.replaceFirst(abbr, "");
            Pattern p = Pattern.compile(abbr + EOS);
            Matcher m = p.matcher(question);
            if (m.matches()) {
                question = m.group(3) + temp;
            }
            qt = "LONGFORM";
            at.add("NEproperName");
        } else if (Pattern.matches(atribut + EOS, question)) { System.out.println("Atribut");
            question = question.replaceFirst("(?i)^(apa)(kah)? " + ATTR, "");
            qw = "what is the name of ";
            qt = "NAME";
            at.add("NEproperName");
        } else if (Pattern.matches(benda1 + EOS, question) || Pattern.matches(benda2 + EOS, question)) { System.out.println("Nomina");
            question = question.replaceFirst("yang ", "");
            qw = "what is the name of ";
            qt = "NAME";
            at.add("NEproperName");
        } else { System.out.println("Default");
            qw = "what is ";
            qt = "DEFINITION";
            at.add("NEproperName");
        }
        question = question.replaceFirst("(?i)(apa)(kah)? ", "");
        return question;
    }

    /**
     * Handles if the question type is <i>Siapa(kah)<i/>.
     *
     * @param string <code>String<code/> question
     * @return nothing
     */
    private static String q_siapa(String question) {
       // System.out.print("Type : Siapa --> ");

        qw = "who is the ";
        qt = "PERSON";
        String companion = "(?i)^(dengan siapa)(kah)? ";
        String atribut = "(?i)^(siapa)(kah)? ([\\w ]+)(dari )";
        String orang = "(?i)^(siapa)(kah)? ([\\w ]+)(yang )";
        String pelaku = "(?i)^(siapa)(kah)? (yang )";
        String milik = "(?i)^([\\w ]+)(siapa)(kah)? (yang )?";
        String def = "(?i)^(siapa)(kah)? ";

        if (Pattern.matches(companion + EOS, question)) { System.out.println("Companion");
            question = question.replaceFirst(companion, "");
            question = question + " with";
        } else if (Pattern.matches(atribut + EOS, question)) { System.out.println("Atribut");
            question = question.replaceFirst("(?i)^(siapa)(kah)?", "");
            question = question.replaceFirst(" dari ", " of ");
        } else if (Pattern.matches(orang + EOS, question)) { System.out.println("Orang");
            question = question.replaceFirst("(?i)^(siapa)(kah)?", "");
            qw = "who ";
        } else if (Pattern.matches(pelaku + EOS, question)) { System.out.println("Pelaku");
            question = question.replaceFirst(pelaku, "");
            qw = "who ";
        } else { System.out.println("Default");
            question = question.replaceFirst(def, "");
        }
        at.add("NEproperName");
        at.add("NEproperName->NEperson");

        return question;
    }

    /**
     * Handles if the question type is <i>Berapa(kah)<i/>.
     *
     * @param string <code>String<code/> question
     * @return nothing
     */
    private static String q_berapa(String question) {
        String temp;

        String WAKTU = "(cepat|lama|abad|dekade|tahun|bulan|minggu|hari|jam|menit|detik)";
        String JUMLAH = "(banyak|besar|buah|ekor|jumlah|kali|macam|orang|populasi)";
        String UKUR = "(berat |harga |kecepatan |kedalaman |kemiringan |ketinggian |lebar |luas |nilai |panjang |suhu |temperatur |tinggi |ukuran |volume )";
        String UNIT_PANJANG = "mil |yard |kaki |inci |km |kilometer |hm |hektometer |dam |dekameter |m |meter |dm |desimeter |decigram |cm |centimeter |sentimeter |mm |milimeter |um |mikrometer |nm |nanometer ";
        String UNIT_BERAT = "ton |kwintal |pon |ons |kg |kilogram |hg |hektogram |dag |dekagram |g |gram |dg |desigram |decigram |cg |centigram |sentigram |mg |miligram ";
        String UNIT_SUHU = "kelvin |celcius |fahrenheit ";
        String UNIT = "(" + UNIT_BERAT + "|" + UNIT_PANJANG + "|" + UNIT_SUHU + ")";
        String MASA = "(abad |dekade |tahun |bulan |minggu |tanggal )";

        String masa = "(?i)^(pada )?" + MASA + "(ke)?berapa(kah)? ";
        String period = "(?i)^(berapa )" + WAKTU + "(kah)? ";
        String nom = "(?i)^(ada )?(berapa )" + JUMLAH + "(kah)?";
        String ukuran = "(?i)^(berapa)(kah)? " + UKUR + "(dari )?";
       // String konversi = "(?i)^(berapa)(kah)? ([0-9]+ )?" + UNIT + "(dalam )" + UNIT;
        String jarak = "(?i)^(berapa)(kah)? (jarak )(antara )?([\\w ]+)(ke |dari |dan )";
        String usia = "(?i)^(berapa)(kah)? (usia|umur) (dari )?";
       // String noun = "(?i)^(berapa) (.*)(kah)? (usia) (dari )?";

        if (Pattern.matches(masa + EOS, question)) { System.out.println("Masa");
            temp = question.replaceFirst(masa, "");
            Pattern p = Pattern.compile(masa + EOS);
            Matcher m = p.matcher(question);
            if (m.matches()) {
                question = m.group(2) + temp; //System.out.println("quest : " + question); //Pada abad keberapakah Marco Polo berlayar?
                if (m.group(2).contains("abad")) {
                    at.add("NEdate->NEcentury");
                } else if (m.group(2).contains("tahun")) {
                    at.add("NEdate->NEyear");
                } else if (m.group(2).contains("abad")) {
                    at.add("NEdate->NEdecade");
                } else if (m.group(2).contains("usia") || m.group(2).contains("umur")) {
                    at.add("NEdate->NEage");
                } else {
                    at.add("NEdate");
                }
            }
            
            question = question.replaceFirst("didirikan ", "founded ");
            qw = "what ";
            qt = "DATE";
            at.add("NEtime");
        } else if (Pattern.matches(period + EOS, question)) { System.out.println("Durasi");
            temp = question.replaceFirst(period, "");
            Pattern p = Pattern.compile(period + EOS);
            Matcher m = p.matcher(question);
            if (m.matches()) {
                if (Pattern.matches("cepat|lama", m.group(2))) {
                    qw = "how long ";
                    at.add("NEduration");
                } else {
                    qw = "how many ";
                    at.add("NEnumber");
                }
                question = m.group(2) + " " + temp; //berapa tahunkah Soekarno menjabat?
            } //System.out.println("qw : " + qw);
            question = question.replaceFirst(" didirikan ", " founded ");
            qt = "DURATION";
        } else if (Pattern.matches(nom + EOS, question)) { System.out.println("Jumlah");
            temp = question.replaceFirst(nom, "");
            Pattern p = Pattern.compile(nom + EOS);
            Matcher m = p.matcher(question);
            if (m.matches()) {
                question = temp;
                if (Pattern.matches("besar", m.group(3))) {
                    qw = "how big is ";
                    temp = temp.replaceFirst("besar", "");
                    at.add("NEsize");
                    //System.out.println("quest : " + temp); //berapa besar istana negara?
                } else {
                    qw = "how many "; //berapa banyak orang yang tinggal di jakarta? berapa jumlah populasi jakarta?
                }
                if (Pattern.matches("kali|macam|orang", m.group(3))) { //berapa kalikah anda ke Jakarta?
                    question = m.group(3) + " " + temp;
                }
            } // System.out.println("qw : " + qw); System.out.println("quest : " + question);
            qt = "NUMBER;MEMBER";
            at.add("NEnumber");
        } else if (Pattern.matches(ukuran + EOS, question)) { System.out.println("Ukuran");
            temp = question.replaceFirst(ukuran, "");
            Pattern p = Pattern.compile(ukuran + EOS);
            Matcher m = p.matcher(question);
            if (m.matches()) {
                question = m.group(3) + "dari " + temp;
                if (m.group(3).contains("berat")) {
                    at.add("NEsize->NEweight");
                    qt = "WEIGHT";
                } else if (m.group(3).contains("harga")) {
                    at.add("NEmoney");
                    qt = "VALUE";
                } else if (m.group(3).contains("kecepatan")) {
                    at.add("NErate->NEspeed");
                    qt = "SPEED";
                } else if (m.group(3).matches("(kedalaman |ketinggian|lebar |panjang |tinggi |luas )")) {
                    at.add("NEsize->NElength");
                    at.add("NEsize->NEarea");
                    qt = "WIDTH;HEIGHT;LENGTH;SIZE";
                } else if(m.group(3).matches("(suhu |temperatur )")){
                    at.add("NEtemperature");
                    qt = "TEMPERATURE";
                } else {
                    at.add("NEsize");
                    qt = "SIZE";
                }
            } //System.out.println("quest : " + question);
            qw = "what is the ";
        } else if (Pattern.matches(jarak + EOS, question)) { System.out.println("Jarak");
            question = question.replaceFirst("(?i)^(berapa)(kah)? (jarak )(antara )?", "");
            qw = "what is the distance between ";
            qt = "DISTANCE";
            at.add("NEsize");
        } else if (Pattern.matches(usia + EOS, question)) { System.out.println("Usia");
            question = question.replaceFirst(usia, "");
            qw = "how old is ";
            qt = "AGE";
            at.add("NEduration");
        } else { System.out.println("Default");
            question = question.replaceFirst("(?i)^(berapa)(kah)? ", "");
            qw = "how many ";
            qt = "NUMBER";
            at.add("NEnumber");
        }

        return question;
    }

    /**
     * Handles if the question type is <i>Mengapa(kah)<i/>.
     *
     * @param string <code>String<code/> question
     * @return nothing
     */
    private static String q_mengapa(String question) {
       // System.out.print("Type : Mengapa --> ");
        String mengapa = "(?i)^(Mengapa|Kenapa)(kah)? ";
        qw = "why ";
        qt = "REASON";
        at.add("-");

        if (Pattern.matches(mengapa + EOS, question)) {
            question = question.replaceFirst(mengapa, ""); System.out.println("Alasan");
        }
        return question;
    }

    /**
     * Handles if the question type is <i>Kapan(kah)<i/>.
     *
     * @param string <code>String<code/> question
     * @return nothing
     */
    private static String q_kapan(String question) {
       // System.out.print("Type : Kapan --> ");

        String kapan = "(?i)^(Kapan)(kah)? ";
        qw = "when ";
        qt = "DATE";
        at.add("NEdate");
        at.add("NEtime");

        if (Pattern.matches(kapan + EOS, question)) {
            question = question.replaceFirst(kapan, ""); System.out.println("Waktu");
        }

        return question;
    }

    /**
     * Handles if the question type is <i>Dimana(kah)<i/>.
     *
     * @param string <code>String<code/> question
     * @return nothing
     */
    private static String q_dimana(String question) {
       // System.out.print("Type : Dimana --> ");
        
        qw = "In which ";

        String AREA = "(benua |distrik |desa |kabupaten |kampung |kecamatan |kelurahan |kota |negara (bagian )?|profinsi |propinsi |provinsi)";
        String LOKASI = "(danau |gedung |gunung |jalan |kota |laut |samudera |samudra |lokasi |sungai |universitas )";

        String dimana = "(?i)^(di( )?mana(kah)?) ";
        String qarea = "(?i)^di (" + AREA + ")mana(kah)? ";
        String qlokasi = "(?i)^di (" + LOKASI + ")mana(kah)? ";
        String lain = "(?i)^di (.*) mana(kah)? ";

        if (Pattern.matches(qarea + EOS, question)) { System.out.println("Area");
            Pattern p = Pattern.compile(qarea + EOS);
            Matcher m = p.matcher(question);

            if (m.matches()) {
                question = question.replaceFirst(qarea, m.group(2)); //System.out.println(m.group(2));
                if (m.group(2).contains("benua")) {
                    at.add("NElocation->NEcontinent");
                } else if (m.group(2).contains("kota")) {
                    at.add("NElocation->NEcity");
                } else if (m.group(2).matches("(negara bagian |provinsi |profinsi |propinsi)")) {
                    at.add("NElocation->NEstate");
                } else if (m.group(2).contains("negara")) {
                    at.add("NElocation->NEcountry");
                } else {
                    at.add("NElocation");
                }
            }
        } else if (Pattern.matches(qlokasi + EOS, question)) { System.out.println("Lokasi");
            Pattern p = Pattern.compile(qlokasi + EOS);
            Matcher m = p.matcher(question);

            if (m.matches()) {
                question = question.replaceFirst(qlokasi, m.group(2)); //System.out.println(m.group(2));
                if (m.group(2).contains("danau")) {
                    at.add("NElocation->NEwater->NElake");
                } else if (m.group(2).contains("laut |samudra |samudera ")) {
                    at.add("NElocation->NEwater->NEsea");
                } else if (m.group(2).contains("sungai")) {
                    at.add("NElocation->NEwater->NEriver");
                } else if (m.group(2).contains("gunung")) {
                    at.add("NElocation->NEmountain");
                } else if (m.group(2).contains("kota")) {
                    at.add("NElocation->NEcity");
                } else if (m.group(2).contains("universitas")) {
                    at.add("NEproperName->NEorganization->NEeducationalInstitution");
                } else {
                    at.add("NElocation");
                }
            }
        } else if (Pattern.matches(lain + EOS, question)) { System.out.println("Lain");
            Pattern p = Pattern.compile(lain + EOS);
            Matcher m = p.matcher(question);

            if (m.matches()) {
                question = question.replaceFirst(lain, m.group(2)); //System.out.println(m.group(2));
            }
        } else {
            question = question.replaceFirst(dimana, ""); System.out.println("Default");
            qw = "where is ";
            at.add("NElocation");
        }
        qt = "PLACE";
        return question;
    }

    /**
     * Handles if the question type is <i>Bagaimana(kah)<i/>.
     *
     * @param string <code>String<code/> question
     * @return nothing
     */
    private static String q_bagaimana(String question) {
       // System.out.print("Type : Bagaimana --> ");
        
        qw = "how ";
        qt = "CAUSE";
        at.add("-");
        String cara = "(?i)^(Bagaimana)(kah)? (cara )";
        String basic = "(?i)^(Bagaimana)(kah)? ";

        if (Pattern.matches(cara + EOS, question)) {
            question = question.replaceFirst(cara, ""); System.out.println("Cara");
        } else if (Pattern.matches(basic + EOS, question)) {
            question = question.replaceFirst(basic, ""); System.out.println("Default");
        }

        return question;
    }

    public static void main(String[] abc) throws Exception {
       // String question_string;
       // try {
       //     System.out.println("Masukkan pertanyaan : ");
       //     while (!(question_string = BR.readLine()).equals("exit")) {
       //         if (!question_string.isEmpty()) {
       //             detectQWord(question_string);
       //         }

       //         System.out.println("########################################################\n");
       //         System.out.println("--------------------------------------------------------\n");
       //         System.out.println("Masukkan pertanyaan : ");
       //     }
       //     System.out.println("--- System Terminated ---");
       // } catch (IOException e) {
       // }
        
        question_list.add("Apakah yang dimaksud dengan nasionalisme?");
        question_list.add("Apakah sinonim dari negara?");
        question_list.add("Apakah ibukota dari Indonesia?");
        question_list.add("Apakah kepanjangan dari UUD?");
        question_list.add("Apakah nama sungai terpanjang di di Afrika?");
        question_list.add("Apakah benda yang biasa dipakai menggali?");
        question_list.add("Apakah UI?");

        question_list.add("Dengan siapa Pangeran Harry menikah?");
        question_list.add("Siapa anak dari Soeharto?");
        question_list.add("Siapa ilmuwan yang mengemukakan teori relativitas?");
        question_list.add("Siapa yang menemukan bola lampu?");
        question_list.add("Siapakah perdana menteri Inggris?");

        question_list.add("Pada abad keberapakah Portugis datang ke Indonesia?");
        question_list.add("Berapa tahunkah Belanda menjajah Indonesia?");
        question_list.add("Berapa jumlah populasi Indonesia?");
        question_list.add("Berapakah panjang dari sungai Nil?");
        question_list.add("Berapakah jarak antara Jakarta dan Bogor?");
        question_list.add("Berapa usia dari Barrack Obama?");
        question_list.add("Berapakah saudara yang dimiliki Michael Jackson?");

        question_list.add("Mengapa manusia diciptakan?");
        
        question_list.add("Kapankah hari buruh sedunia?");

        question_list.add("Di benua manakah Argentina berada?");
        question_list.add("Di laut manakah Titanic tenggelam?");
        question_list.add("Di ruang manakah rapat dilaksanakan?");
        question_list.add("Dimanakah Washington DC?");

        question_list.add("Bagaimana cara membuat kue donat?");
        question_list.add("Bagaimana keadaan ekonomi Indonesia?");
//        question_list.add("Pada umur berapakah Joseph di Mambro meninggal?");
        
        for (String aQuestion : question_list) {
            detectQWord(aQuestion);
        }
    }
}
