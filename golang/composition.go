package golang

// 使用 struct 实现类似于 OO 的功能
// 部门
// type ZhaoshangService struct {
//   HeadCount int // 部门人数
// }

// composition 可以类比为Ruby中的include
// 即为方法的复用
type SellerModule struct {
	*Department // composition
	owner       string
	Name        string
}
