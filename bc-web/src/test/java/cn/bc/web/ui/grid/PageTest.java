package cn.bc.web.ui.grid;

import org.junit.Test;

import cn.bc.web.ui.html.page.Button;
import cn.bc.web.ui.html.page.ListPage;
import cn.bc.web.ui.html.page.Option;

public class PageTest {
	@Test
	public void testListPage() {
		ListPage listPage = new ListPage();
		listPage.setGrid(GridTest.buildTestGrid())
				.setCreateAction("/duty/create")
				.setDeleteAction("/duty/delete")
				.setEditAction("/duty/edit")
				.setJs("js1").setCss("css1").setIniMethod("iniMethod")
				.setOption(
						new Option()
								.setButtons(
										new Button[] {
												new Button("ok", "delete"),
												new Button("edit", "edit"),
												new Button("create", "create") })
								.setMinWidth(200).setMinHeight(250)
								.setWidth(500).setHeight(400).setModal(false)
								.toString()).setBeautiful(true);

		StringBuffer main = new StringBuffer();
		System.out.println(listPage.render(main));
	}
}
