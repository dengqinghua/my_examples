package golang

// Account 账户信息
type Account struct {
	Name string
	ID   int
}

// Db 数据库接口
type Db interface {
	Get(id int) (Account, error)
}

// DbTwo 数据库接口, 这里传入的 *Account
type DbTwo interface {
	GetTwo(id int) (*Account, error)
}

// GetUserNameByID 通过id获取名字
func GetUserNameByID(db Db, id int) string {
	record, err := db.Get(id)

	if err != nil {
		return ""
	}

	return record.Name
}

// GetUserNameByIDTwo 通过id获取名字
func GetUserNameByIDTwo(db DbTwo, id int) string {
	record, err := db.GetTwo(id)

	if err != nil {
		return ""
	}

	return record.Name
}
