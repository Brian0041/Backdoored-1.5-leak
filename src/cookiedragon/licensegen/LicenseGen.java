package cookiedragon.licensegen;

import java.security.MessageDigest;
import java.nio.charset.Charset;
import java.util.Random;

public class LicenseGen
{
    public final String hwid;
    private final long seed;
    private Random generator;
    private static final String[] HASHES;
    
    public LicenseGen(final long seed) {
        super();
        this.seed = seed;
        this.generator = new Random(this.seed);
        this.hwid = this.getHwid();
    }
    
    public LicenseGen(final long seed, final String hwid) {
        super();
        this.seed = seed;
        this.generator = new Random(this.seed);
        this.hwid = hwid;
    }
    
    private String getHwid() {
        return HWID.bytesToHex(HWID.generateHWID());
    }
    
    public String genLicense() {
        try {
            String a = Long.toString(this.seed) + this.hwid;
            for (int loop = this.generator.nextInt(20), i = 1; i <= loop; ++i) {
                a = this.hash(a.getBytes(), this.randomAlgorithm());
                if (this.generator.nextBoolean()) {
                    final byte[] bytes = new byte[7];
                    this.generator.nextBytes(bytes);
                    a += new String(bytes, Charset.forName("UTF-8"));
                }
            }
            for (int loop = this.generator.nextInt(3), i = 1; i <= loop; ++i) {
                if (this.generator.nextBoolean()) {
                    a += this.hash(a.getBytes(), this.randomAlgorithm());
                }
                else {
                    a = this.hash(a.getBytes(), this.randomAlgorithm()) + a;
                }
            }
            a = this.hash((a + a + a).getBytes(), this.randomAlgorithm());
            return a;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private String hash(final byte[] bytes, final String algorithm) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bytes);
            return HWID.bytesToHex(messageDigest.digest());
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    private String randomAlgorithm() {
        final int index = this.generator.nextInt(LicenseGen.HASHES.length);
        return LicenseGen.HASHES[index];
    }
    
    static {
        HASHES = new String[] { "MD2", "MD5", "SHA-1", "SHA-256", "SHA-384", "SHA-512" };
    }
}
