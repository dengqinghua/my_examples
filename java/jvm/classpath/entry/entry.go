package entry

// Entry is an entry for classpath
type Entry interface {
	// 寻找和加载 class文件
	readClass(className string) ([]byte, Entry, error)
	// 类似于 java 的 toString 方法
	String() string
}
