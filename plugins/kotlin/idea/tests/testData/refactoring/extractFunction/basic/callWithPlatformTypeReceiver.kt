// WITH_RUNTIME
// SUGGESTED_RETURN_TYPES: kotlin.Boolean?, kotlin.Boolean
// PARAM_DESCRIPTOR: value-parameter val it: kotlin.collections.Map.Entry<(Boolean..Boolean?), (Boolean..Boolean?)> defined in test.<anonymous>
// PARAM_TYPES: kotlin.collections.Map.Entry<(Boolean..Boolean?), (Boolean..Boolean?)>
fun test() {
    J.getMap().filter { <selection>it.key</selection> }
}