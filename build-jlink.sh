#!/bin/bash
# Unfortunately, jlink cannot process automatic modules (`jlink -p ... --add-modules ALL-MODULE-PATH`).
# Therefore, we have to add some modules and pass the remaining stuff to `java -cp ...` or `java -p ...`.
# Modules passed to jlink can the be removed from the module path or class path.
jlink \
  -p target/sample-javafx-*-dist/lib/ \
  --add-modules javafx.fxml,javafx.controls,jakarta.persistence,org.glassfish.jaxb.runtime,java.naming,org.slf4j.simple \
  --output target/manual-jlink \
  --strip-debug --no-man-pages --no-header-files
cp -r target/sample-javafx-*-dist/lib/ target/manual-jlink/lib/jars
(cd target/manual-jlink/lib/jars && rm javafx* jaxb* slf4j*)
rm -rf target/manual-jlink/legal
cat > target/manual-jlink/bin/run.sh << 'EOF'
#!/bin/sh
"$(dirname "$0")/java" \
  -p "$(dirname $0)/../lib/jars" \
  --add-modules ALL-MODULE-PATH \
  com.github.phoswald.sample.Application
EOF
chmod a+x target/manual-jlink/bin/run.sh
