package com.dengqinghua;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class EnumTest {
    /**
     * Also see {@link java.util.concurrent.TimeUnit TimeUnit}
     */
    enum AEnum {
        DS {
            String say(String words) { return "DSGV587! " + words; }
            public String toS() { return "I am DS, a handsome boy"; }
        },

        SN {
            String say(String words) { return "SNV587! " + words; }
            // 下面的代码编译会不通过
            // String toS() { return "I am SN, a beautiful girl"; }
        },

        WJJ(525) {
            String say(String words) { return "Fix EveryThing! " + value + words; }
            public String toS() { return "I am WJJ, the cleverest one in the world"; }
        },

        DQH {
            String say(String words) { return "Confused One. " + words; }
        };

        String loveMe() { return "Tender"; }
        String letMe() { return "Go"; }

        // 这里定义了 AEnum 里面的元素必须要实现的方法
        abstract String say(String words);

        int value = -1;

        // 这里可以设置不同的 Constructor
        AEnum(int i) { this.value = i; }
        AEnum()      { this.value = 1024; }

        /**
         *  注意:
         *
         *  <p>
         *  如果是下面 访问级别, 高于 DS 里面的 toS() 方法的访问级别, 则会编译报错
         *
         *  <p>
         *  如果是下面 访问级别, 等于或者低于 DS 里面的 toS() 方法的访问级别, 则使用
         *
         *  <pre>
         *      AEnum.DS#toS()
         *  </pre>
         *
         *  <p>
         *  如果DS里面 没有 toS() 方法的访问级别, 则使用
         *
         *  <pre>
         *      AEnum#toS()
         *  </pre>
         *
         *  在 {@link java.util.concurrent.TimeUnit TimeUnit} 中, 存在两个 toNanos 方法
         *
         *  <p>
         *      在外层的 toNanos 方法直接是 throw Exception
         *
         *  <pre>
         *      public long toNanos(long duration) {
         *         throw new AbstractMethodError();
         *      }
         *  </pre>
         *
         *  <p>
         *      在内层的 toNanos 方法为
         *
         *  <pre>
         *      DAYS {
         *          public long toNanos(long d)   { return x(d, C6/C0, MAX/(C6/C0)); }
         *      }
         *  </pre>
         *  </p>
         *
         *  我想 Doug Lea 是希望以后如果要新的元素的时候, 必须要要实现 toNanos 方法,
         *  但是这使用 abstract 是不是更合理一些?
         *
         *  <p>
         *      他的解释如下:
         *
         *      <pre>
         *        To maintain full signature compatibility with 1.5, and to improve the
         *        clarity of the generated javadoc (see 6287639: Abstract methods in
         *        enum classes should not be listed as abstract), method convert
         *        etc. are not declared abstract but otherwise act as abstract methods.
         *      </pre>
         *  </p>
         *
         *  即为了低版本的兼容问题.
         *
         *  <p>
         *      但是在
         *      {@link java.util.concurrent.TimeUnit#excessNanos(long, long) excessNanos} 方法中,
         *      又用到了 abstract 关键字, 我也是醉了
         *  </p>
         **/
        public String toS() {
            return "I am just a normal person";
        }
    }

    @Test public void testToS() throws Exception {
        assertThat(AEnum.DQH.toS(), is("I am just a normal person"));
        assertThat(AEnum.DS.toS(), is("I am DS, a handsome boy"));
        assertThat(AEnum.WJJ.toS(), is("I am WJJ, the cleverest one in the world"));
    }

    @Test public void testValue() throws Exception {
        assertThat(AEnum.WJJ.value, is(525));
        assertThat(AEnum.DS.value, is(1024));
        assertThat(AEnum.SN.value, is(1024));
    }

    @Test public void testSay() throws Exception {
        assertThat(Arrays.asList(AEnum.values()), contains(AEnum.DS, AEnum.SN, AEnum.WJJ, AEnum.DQH));
        assertThat(AEnum.DS.say("Yeah!"), is("DSGV587! Yeah!"));
        assertThat(AEnum.SN.say("Yeah!"), is("SNV587! Yeah!"));

        assertThat(AEnum.SN.loveMe(), is("Tender"));
        assertThat(AEnum.DS.letMe(), is("Go"));

        assertThat(AEnum.WJJ.say(" Yeah!"), is("Fix EveryThing! 525 Yeah!"));
    }
}
