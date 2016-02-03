"use strict";

jQuery(window).load(function() {
    doStripNewLines();

    // React to lines being added in running builds
    jQuery(".console-output").on("DOMNodeInserted", function(e) {
        var $target = jQuery(e.target);

        if ($target.is("pre")) {
            $target.find(".line > span").each(function(index, el) {
                doStripNewLine(jQuery(el));
            })
        }
    });
});

function doStripNewLines() {
    jQuery(".line").each(function(index, el) {
        var $el = jQuery(el).find("> span");

        doStripNewLine($el);
    });
}

function doStripNewLine($el) {
    $el.html($el.html().replace(/^\n/, ""));
    $el.html($el.html().replace(/\n$/, ""));

    if (!$el.html().length) {
        $el.text(" ");
    }
}
