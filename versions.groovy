def parse(version) {
  // [2.3.4-5, 2.3.4-5.build6]
  versions = [version, "${version}.build${env.BUILD_NUM}"]

  // [2.3.4, 5]
  tokens = version.tokenize("-")
  upstream_tokens = tokens[0].tokenize(".")

  // [2.3.4-5, 2.3.4-5.build6, 2, 2.3, 2.3.4]
  for (int i=0; i<upstream_tokens.size(); ++i) {
    versions << upstream_tokens[0..i].join(".")
  }

  return versions
}

return this;
