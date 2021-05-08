function changeArticleSubmit(type) {
    let changeArticleForm = document.form_change_article;
    let checks = document.getElementsByName("article_check");
    let articleId = null;

    // 選択すみの物があるかどうかチェック
    for (let index=0; index < checks.length; index++) {
        if (checks[index].checked) {
            articleId = checks[index].getAttribute("data-id");
            break;
        }
    }

    // 未選択の場合
    if (articleId == null) {
        alert("記事を選択してください");

        return;
    }

    // 更新か削除によって飛び先を決定する
    switch (type) {
        case "update":
            changeArticleForm.action = "/edit/" + articleId;
            changeArticleForm.submit();
            break;
        default:
            break;
    }
}